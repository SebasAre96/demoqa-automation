pipeline {

    agent any

    // Requiere en Jenkins: tool Maven llamado "Maven", JDK 17 en el agente
    // y los plugins Pipeline, JUnit, Allure y GitHub.
    tools {
        maven 'Maven'
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Browser para ejecutar los tests'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Correr en modo headless (obligatorio en Docker/Jenkins)'
        )
        choice(
            name: 'SUITE',
            choices: ['smoke', 'regression'],
            description: 'smoke = escenarios @smoke | regression = todos los escenarios (excepto @ignore)'
        )
    }

    environment {
        MAVEN_OPTS     = '-Xmx512m'
        ALLURE_RESULTS = 'target/allure-results'
    }

    triggers {
        // Solo dispara si Jenkins es accesible desde internet (webhook de GitHub).
        // En Jenkins local se puede usar un tunel (ngrok/smee) o lanzar el build a mano.
        githubPush()
    }

    stages {

        stage('Checkout') {
            steps {
                cleanWs()
                checkout scm
                script {
                    def commit = sh(
                        script: 'git log -1 --pretty=format:"%h - %an: %s"',
                        returnStdout: true
                    ).trim()
                    echo "Commit: ${commit}"
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Tests') {
            steps {
                script {
                    // @ignore se excluye siempre (escenarios inestables en headless)
                    def tagExpr = (params.SUITE == 'smoke')
                        ? '@smoke and not @ignore'
                        : 'not @ignore'

                    sh """
                        mvn -B test \\
                            -Dbrowser=${params.BROWSER} \\
                            -Dheadless=${params.HEADLESS} \\
                            -Dcucumber.filter.tags='${tagExpr}'
                    """
                }
            }
        }
    }

    post {
        always {
            // Se ejecuta aunque fallen los tests: es cuando mas se necesita el reporte
            junit(
                testResults: 'target/surefire-reports/*.xml',
                allowEmptyResults: true
            )
            allure([
                includeProperties: false,
                jdk              : '',
                results          : [[path: "${ALLURE_RESULTS}"]],
                reportBuildPolicy: 'ALWAYS',
                report           : 'allure-report'
            ])
            archiveArtifacts(
                artifacts: 'target/cucumber-reports.html',
                allowEmptyArchive: true
            )
        }
        success {
            echo "PIPELINE EXITOSO - Suite: ${params.SUITE} | Browser: ${params.BROWSER} | Build #${BUILD_NUMBER}"
        }
        failure {
            echo "PIPELINE FALLO - Revisar reporte Allure. Build #${BUILD_NUMBER}"
        }
    }
}
