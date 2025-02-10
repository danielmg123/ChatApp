#!/usr/bin/env bash
set -e

echo "Checking if primary is ready..."
until pg_isready -h postgres-primary -p 5432; do
  echo "Waiting for primary..."
  sleep 2
done

echo "Primary is ready; now starting PostgreSQL replica process..."
# Hand off to the official Postgres entrypoint
exec docker-entrypoint.sh "$@"