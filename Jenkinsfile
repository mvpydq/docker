pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh '/usr/local/maven/bin/mvn -B -DskipTests clean package'
      }
    }
  }
}