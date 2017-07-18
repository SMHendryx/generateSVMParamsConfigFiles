# generatePowerSetConfigFiles
Scala application that generates the power set of a set of strings and writes to application.conf files.  Useful for machine learning feature selection.

Run with Scala Build Tool:

sbt "run /path/to/complete/set/application.con /path/to/write/output/of/config/files"

e.g.:

sbt "run /Users/seanmhendryx/Research/context/configFiles/in/application.conf /Users/seanmhendryx/Research/context/configFiles/out"
generateSVMParamsConfigFiles
