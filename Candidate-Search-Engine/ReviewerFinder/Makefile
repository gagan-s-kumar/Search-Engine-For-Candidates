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

package:
	mvn -Dmaven.test.skip=true package
	cp target/ReviewerFinder-0.0.1-SNAPSHOT.jar dist/reviewerfinder.jar
