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
            } 
        }
        stage('Api tests') { 
            steps { 
                sh 'make test-api' 
                archiveArtifacts artifacts: 'results/api_result.xml' 
            } 
        }
        stage('e2e tests') { 
            steps { 
                sh 'make test-e2e' 
                archiveArtifacts artifacts: 'results/cypress_result.xml' 
            } 
        }
    } 
    post { 
        always { 
            junit 'results/*_result.xml' 
            cleanWs() 
        }
        success {  
            echo "El nombre del trabajo es: ${JOB_NAME}"
            echo "El numero de ejecucion es: ${BUILD_NUMBER}"
             
        }  
        failure {  
            mail bcc: '', body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "ERROR CI: Project name -> ${env.JOB_NAME}", to: "foo@foomail.com";  
        }  
    } 
} 