default:

compile: clean 
	mvn compile

run-test:
	mvn test

clean:
	mvn clean

checkstyle: 
	mvn checkstyle:check

pdf:
	pandoc -s -o README.pdf README.md
