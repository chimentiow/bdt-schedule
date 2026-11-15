@echo off
rem Навантажувальне тестування в Apache JMeter (Windows).
rem Використання: run-load-test.bat C:\шлях\до\apache-jmeter-5.6.3
setlocal
set HERE=%~dp0
set PROJECT=%HERE%..
set JMETER_DIR=%~1
if "%JMETER_DIR%"=="" set JMETER_DIR=%JMETER_HOME%
if not exist "%JMETER_DIR%\bin\ApacheJMeter.jar" (
  echo Вкажіть шлях до папки apache-jmeter-5.6.3
  exit /b 1
)
if not exist "%PROJECT%\bdt.jar" call "%PROJECT%\build.bat"
copy /y "%PROJECT%\bdt.jar" "%JMETER_DIR%\lib\" > nul
copy /y "%PROJECT%\lib\h2-2.2.224.jar" "%JMETER_DIR%\lib\" > nul
for %%T in (1 10 25 50) do (
  if exist "%HERE%results\threads-%%T" rmdir /s /q "%HERE%results\threads-%%T"
  mkdir "%HERE%results\threads-%%T"
  java -Xmx1g -jar "%JMETER_DIR%\bin\ApacheJMeter.jar" -n -t "%HERE%bdt-load-test.jmx" -JprojectDir="%PROJECT%" -Jthreads=%%T -Jrampup=5 -Jloops=30 -l "%HERE%results\threads-%%T\results.jtl" -j "%HERE%results\threads-%%T\jmeter.log" -e -o "%HERE%results\threads-%%T\report"
)
echo Готово. Результати: %HERE%results
