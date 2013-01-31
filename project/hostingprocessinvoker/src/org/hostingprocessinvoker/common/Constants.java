package org.hostingprocessinvoker.common;

public interface Constants {
	
	String					CONFIGURATIONS_FILE = "settings.ssd";
	String					CONFIGURATIONS_FILE_ADDRESS = "data" + System.getProperty("file.separator") + CONFIGURATIONS_FILE;
	String					CONFIGURATIONS_MAPPED_FOLDER = "mapped-folders";

	String					CONSOLE_PARAMETER_CURRENT_DIR = "-current_directory";
	
	String					EXECUTABLES_INVOKER_FILE = "executables";
	String					DESCRIPTION_INVOKER_FILE = "description";
	String					CANONICAL_PATH_INVOKER_FILE = "canonical_path";
	String					PARAMETERS_INVOKER_FILE = "parameters";
	String					KEY_INVOKER_FILE = "key";
	String					VALUE_INVOKER_FILE = "value";
	
	String					REGEX_FILE = "^[\\w|\\W]+.txt$";
	
	String					DATA_WATCHER_FOLDER_MAPPING = "data_watcher_folder_mapping";
	String					DATA_WATCHER_CRON_EXPRESSION = "0/5 * * ? * *";
}
