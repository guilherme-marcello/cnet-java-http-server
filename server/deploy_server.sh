#!/bin/bash
set -x
sudo docker-compose down
sudo docker rmi mp1:latest
sudo docker-compose up 
