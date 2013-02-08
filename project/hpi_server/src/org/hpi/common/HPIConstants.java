package org.hpi.common;

public interface HPIConstants {
	
	/**
	 * Constants Changeable by Ant:build
	 * The following Constants have values that are changed in build time.
	 */
	String 					HPI_VERSION = "ant:build:constant:version";
	String 					HPI_RELEASE = "ant:build:constant:release";
	
	String					ENV_HPI_BASE = "hpi.base";
	
	String 					SSD_EXTENSION = ".ssd";
	
	String					CONFIGURATIONS_FILE = "hpi_data_settings" + SSD_EXTENSION;
	String					CONFIGURATIONS_FILE_ADDRESS = "conf" + System.getProperty("file.separator") + CONFIGURATIONS_FILE;
	String					CONFIGURATIONS_MAPPED_FOLDER = "mapped_folders";
	String					CONFIGURATIONS_RELATIVE_SERVER = "relative_server";
	String					CONFIGURATIONS_CANONICAL_PATH = "canonical_path";
	String					CONFIGURATIONS_USERS = "users";
	String					CONFIGURATIONS_USERS_NICKNAME = "nickname";
	String					CONFIGURATIONS_USERS_PASSPHRASE = "passphrase";
	String					CONFIGURATIONS_CONFIG_SERVER = "configuration_server";
	String					CONFIGURATIONS_PORT_NUMBER = "port_number";
	String					CONFIGURATIONS_SESSION_MANAGER = "session_manager";
	String					CONFIGURATIONS_KEEP_SESSION_ALIVE = "keep_session_alive";

	String					CONSOLE_PARAMETER_CURRENT_DIR = "-current_directory";
	
	String 					END_OF_LINE = "\n";
	
	String					EXECUTABLES_INVOKER_FILE = "executables";
	String					DESCRIPTION_INVOKER_FILE = "description";
	String					CANONICAL_PATH_INVOKER_FILE = "canonical_path";
	String					PARAMETERS_INVOKER_FILE = "parameters";
	String					KEY_INVOKER_FILE = "key";
	String					VALUE_INVOKER_FILE = "value";
	
	String					REGEX_FILE = "^[\\w|\\W]+" + SSD_EXTENSION + "$";
	
	String					DATA_WATCHER_FOLDER_MAPPING = "data_watcher_folder_mapping";
	String					DATA_WATCHER_CRON_EXPRESSION = "0/10 * * ? * *";
}
