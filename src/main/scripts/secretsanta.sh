curl -H "Content-Type: application/json" -X POST -d '{"participants": [{"name" : "Mickey"},{"name" : "Minnie"},{"name" : "Donald"},{"name" : "Daisy"}]}' http://localhost:8092 | json_pp