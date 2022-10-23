#!/bin/bash
set -x
sudo docker-compose -f docker/docker-compose.yaml down
sudo docker rmi mp1:latest
sudo docker-compose -f docker/docker-compose.yaml up 
