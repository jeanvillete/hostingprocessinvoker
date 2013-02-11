# ---------------------------------------------------------------------------
# Script to Shutdown the Hosting Process Invoker on Linux Platform
# 2013 02 11
# ---------------------------------------------------------------------------
if [ -z "$JAVA_HOME" ]; then
  echo "JAVA_HOME not defined"
  echo "No valid value was defined to JAVA_HOME environment variable, please, do it before!"
  exit 1
fi

APP_BIN=$(readlink -f $(dirname $0))
JVM_PARAMETERS="-Dhpi.base=$APP_BIN/.. -Dlog4j.configuration=file:$APP_BIN/../conf/log4j.properties"

echo "Using JAVA_HOME:        $JAVA_HOME"
echo "HPI Server Directory:   $APP_BIN"
echo "JVM Parameters:         $JVM_PARAMETERS"

exec "$JAVA_HOME"/bin/java $JVM_PARAMETERS -cp "$APP_BIN"/hpi_server.jar org.hpi.service.main.HPIShutdownServer
