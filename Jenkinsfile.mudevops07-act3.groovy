pipeline { 
    agent { 
        label 'docker' 
    } 
    stages { 
        stage('Source') { 
            steps { 
                git(url: 'https://github.com/DevOscarFA/unir-test.git', branch: "main")
                
                sh "ls -lat"
            } 
        } 
        stage('Build') { 
            steps { 
                echo 'Building stage!' 
                sh 'make build' 
            } 
        }
        stage('Unit tests') { 
            steps { 
                sh 'make test-unit' 
                archiveArtifacts artifacts: 'results/*.xml'
                archiveArtifacts artifacts: 'results/*.html'
            } 
        }
        stage('Api tests') { 
            steps { 
                sh 'make test-api' 
                archiveArtifacts artifacts: 'results/*.xml' 
                archiveArtifacts artifacts: 'results/*.html'
            } 
        }
        stage('e2e tests') { 
            steps { 
                sh 'make test-e2e' 
                archiveArtifacts artifacts: 'results/*.xml' 
            } 
        }
    } 
    post { 
        always { 
            junit 'results/*_result.xml' 
            cleanWs() 
            echo 'Sending email'
            echo 'mail to: oscardevops@gmail.com'
            echo "subject: Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
            echo "body: ${currentBuild.currentResult}: ${env.JOB_NAME} Build Number: ${env.BUILD_NUMBER}"
        }
        success {  
            echo "El nombre del trabajo es: ${JOB_NAME}"
            echo "El numero de ejecucion es: ${BUILD_NUMBER}"
             
        }  
        failure {  
            echo "${currentBuild.currentResult}: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}"
        }  
    } 
} 