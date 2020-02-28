#!/bin/bash
chmod +x lib/*.jar
rm -rf tmp
mkdir tmp
javac -cp src/main/java:lib/gson-2.8.6.jar:lib/jopt-simple-6.0-alpha-3.jar \
    -target 1.9 -source 1.9 \
    -d tmp --module-path src/main/java \
    src/main/java/fr/ynerant/leveleditor/api/editor/*.java \
    src/main/java/fr/ynerant/leveleditor/client/main/*.java \
    src/main/java/fr/ynerant/leveleditor/editor/*.java \
    src/main/java/fr/ynerant/leveleditor/frame/*.java \
    src/main/java/fr/ynerant/leveleditor/game/*.java \
    src/main/java/fr/ynerant/leveleditor/game/mobs/*.java \
    src/main/java/fr/ynerant/leveleditor/game/towers/*.java
cp -r src/main/resources/* tmp/
unzip lib/gson-2.8.6.jar -x META-INF/MANIFEST.MF -d tmp
unzip lib/jopt-simple-6.0-alpha-3.jar  -x META-INF/MANIFEST.MF -d tmp
cd tmp
zip -r TheGame.jar *
mv TheGame.jar ../
cd ..
rm -rf tmp
chmod +x TheGame.jar
echo "Successfully compiled to \"TheGame.jar\". To run: \"java -jar TheGame.jar\"".
