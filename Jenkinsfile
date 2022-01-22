node {
   stage('Clone') {
      checkout scm
   }

   stage('Build') {
       sh "/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/maven/bin/mvn clean install -U"
   }

   stage('Archive') {
      archiveArtifacts artifacts: 'spigot/target/*.jar', 'kotlin/target/*.jar'+, fingerprint: true
   }
}