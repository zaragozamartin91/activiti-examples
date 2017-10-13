#!/bin/bash
docker run -it --rm --link activiti-postgres:postgres postgres psql -h postgres -U root activiti
