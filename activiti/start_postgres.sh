#!/bin/bash
docker run --rm --name activiti-postgres -e POSTGRES_PASSWORD=root -e POSTGRES_USER=root -e POSTGRES_DB=activiti -p 5432:5432 -d postgres
