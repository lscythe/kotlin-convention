#!/usr/bin/env bash
#
# Prepares the project for release.
#
# Usage: ./release.sh [version]
#        ./release.sh --snapshot
#
# If version is not provided, auto-increments patch version.
# Use --snapshot to publish a SNAPSHOT version without git commits/tags.

set -euo pipefail

cd "$(dirname "$0")"

changelog="CHANGELOG.md"
github_repository_url="https://github.com/lscythe/convention"

function get_last_version() {
    git describe --tags --abbrev=0 2>/dev/null || echo "0.0.0"
}

function increment_version() {
    local version="$1"
    local major minor patch
    IFS='.' read -r major minor patch <<< "$version"
    patch=$((patch + 1))
    echo "$major.$minor.$patch"
}

function diff_link() {
    echo -n "$github_repository_url/compare/${1}...${2}"
}

# Handle --snapshot option
snapshot=false
if [[ "${1:-}" == "--snapshot" ]]; then
    snapshot=true
    shift
fi

# Handle --help option
if [[ "${1:-}" == "--help" || "${1:-}" == "-h" ]]; then
    echo "Usage: ./release.sh [version]"
    echo "       ./release.sh --snapshot"
    echo ""
    echo "Options:"
    echo "  version     Semantic version (e.g., 1.0.0)"
    echo "  --snapshot  Publish SNAPSHOT without git tag"
    echo "  --help      Show this help message"
    exit 0
fi

echo "Updating local repository..."
git fetch --quiet --tags origin
echo "Repository updated."
echo

last_version=$(get_last_version)
if [[ -n "${1:-}" ]]; then
    version="$1"
else
    version=$(increment_version "$last_version")
fi

if [[ "$snapshot" == true ]]; then
    version="$version-SNAPSHOT"
fi

echo "Release $last_version -> $version"
echo

# Update CHANGELOG.md
if [[ -f "$changelog" ]]; then
    today=$(date '+%Y-%m-%d')
    
    awk -v ver="$version" -v date="$today" '
        /^## \[Unreleased\]/ {
            print "## [Unreleased]"
            print ""
            print "## [" ver "] - " date
            next
        }
        { print }
    ' "$changelog" > "$changelog.tmp"
    mv "$changelog.tmp" "$changelog"
    echo "Updated CHANGELOG.md header"
    
    if grep -q "^\[unreleased\]:" "$changelog"; then
        sed -i".bak" "s|\[unreleased\]:.*|\[unreleased\]: $(diff_link "$version" "main")|" "$changelog"
        
        if ! grep -q "^\[$version\]:" "$changelog"; then
            echo "[$version]: $(diff_link "$last_version" "$version")" >> "$changelog"
        fi
        rm -f "$changelog.bak"
        echo "Updated diff links in CHANGELOG.md"
    fi
fi

echo
echo "Changes to be committed:"
git diff --stat

echo
echo "Do you want to commit and create release tag $version?"
echo "The tag push will trigger the publish workflow on CI."
read -p "Enter 'yes' to continue: " -r input
if [[ "$input" != "yes" ]]; then
    echo "Aborted. Changes not committed."
    git checkout -- "$changelog" 2>/dev/null || true
    exit 0
fi

echo
echo "Creating release..."
git add -A
git commit --quiet --message "release: $version"
git tag "$version" -m "Release $version"
echo "Created tag $version"

echo "Pushing to remote..."
git push --quiet origin HEAD
git push --quiet origin "$version"
echo
echo "DONE!"
echo
echo "Release will be published automatically via GitHub Actions."
echo "View release: $github_repository_url/releases/tag/$version"
