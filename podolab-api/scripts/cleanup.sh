#!/bin/bash

cd /home/ubuntu/api-server || exit 1

docker compose -f docker-compose.yml -f docker-compose.prod.yml down || true