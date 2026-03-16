#!/bin/bash
set -e

cd /home/ubuntu/api-server || exit 1

aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 782595374275.dkr.ecr.ap-northeast-2.amazonaws.com

docker compose -f docker-compose.yml -f docker-compose.prod.yml pull
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d