# ---------------------------------------------------------------------------
# Script to invoke the Hosting Process Invoker Client on Linux Platform
# 2013 02 12
# ---------------------------------------------------------------------------
if [ -z "$JAVA_HOME" ]; then
  echo "JAVA_HOME not defined"
  echo "No valid value was defined to JAVA_HOME environment variable, please, do it before!"
  exit 1
fi

APP_DIR=$(readlink -f $(dirname $0))
exec "$JAVA_HOME"/bin/java -jar "$APP_DIR"/hpi_client.jar $*
