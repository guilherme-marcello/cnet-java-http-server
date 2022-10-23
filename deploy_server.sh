#!/bin/bash
set -x
sudo docker-compose down
sudo docker rmi mp1:complete
sudo docker-compose up 
