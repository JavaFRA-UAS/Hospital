rm -rf bin/*
mvn clean compile
cp -rf target/classes/* bin/

(cd bin; rm ../hospital.jar; zip -r ../hospital.jar *) >/dev/null 2>&1

java -cp lib/jgoodies-common-1.9.0.jar:lib/jgoodies-forms-1.9.0.jar:lib/sqlite-jdbc-3.15.1.jar:lib/LGoodDatePicker-8.3.0.jar:bin:. hospital.Main
