#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

if ! command -v mvn >/dev/null 2>&1; then
    echo "Maven is required to run Scrolling Game." >&2
    exit 1
fi

exec mvn compile exec:java "$@"
