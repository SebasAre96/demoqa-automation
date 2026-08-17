pipeline {

    agent any

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
            choices: ['smoke', 'regression', 'all'],
            description: 'Suite de Cucumber a ejecutar'
        )
    }

    environment {
        MAVEN_OPTS     = '-Xmx512m'
        ALLURE_RESULTS = 'target/allure-results'
    }

    triggers {
        githubPush()
    }

    stages {

        stage('📥 Checkout') {
            steps {
                cleanWs()
                checkout scm
                script {
                    def commit = sh(
                        script: 'git log -1 --pretty=format:"%h - %an: %s"',
                        returnStdout: true
                    ).trim()
                    echo "📌 Commit: ${commit}"
                }
            }
        }

        stage('🔨 Build') {
            steps {
                echo "🔨 Compilando proyecto DemoQA..."
                sh "${tool 'Maven'}/bin/mvn clean compile -q"
                echo "✅ Compilación exitosa"
            }
        }

        stage('🧪 Tests') {
            steps {
                script {
                    def mvn = "${tool 'Maven'}/bin/mvn"
                    def tags = ''
                    if (params.SUITE == 'smoke')      tags = '-Dcucumber.filter.tags="@smoke"'
                    if (params.SUITE == 'regression') tags = '-Dcucumber.filter.tags="@regression"'

                    sh """
                        ${mvn} test \
                            -Dbrowser=${params.BROWSER} \
                            -Dheadless=${params.HEADLESS} \
                            ${tags} \
                            -q
                    """
                }
            }
            post {
                always {
                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )
                }
            }
        }

        stage('📊 Allure Report') {
            steps {
                echo "📊 Generando reporte Allure..."
                allure([
                    includeProperties: false,
                    jdk              : '',
                    results          : [[path: "${ALLURE_RESULTS}"]],
                    reportBuildPolicy: 'ALWAYS',
                    report           : 'allure-report'
                ])
            }
        }
    }

    post {
        success {
            echo "✅ PIPELINE EXITOSO — Suite: ${params.SUITE} | Browser: ${params.BROWSER} | Build #${BUILD_NUMBER}"
        }
        failure {
            echo "❌ PIPELINE FALLÓ — Revisar reporte Allure. Build #${BUILD_NUMBER}"
        }
        always {
            echo "🧹 Pipeline finalizado."
        }
    }
}