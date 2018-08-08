node {
  checkout scm
  stage('Unit Test') {
    dir('ReviewerFinder') {
      sh 'ls'
      sh 'make clean'
      sh 'make compile-test'
      sh 'make run-test'
    }
  }
}
