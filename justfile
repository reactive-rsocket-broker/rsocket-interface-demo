build:
  mvn -DskipTests clean package

hello:
  echo "hello"

py-test:
  rsocket-py --request --route hello --data 'Jackie' ws://localhost:8080/rsocket

rsc-test:
  rsc --request --route hello --data 'Jackie' ws://localhost:8080/rsocket
  