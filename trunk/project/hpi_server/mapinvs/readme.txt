#########  Regarding #########
The folder name means: Mapped Invokers
This folder is a default folder and as well is intended to holds all desired invokers for the fist moment.

######### Extending #########
You can set other(s) mapped folders adjusting properly the array object "mapped_folders" inside the file "hpi_data_settings.ssd". As this object is an array, it means that you can set a lot of folders, as following;

*Note that you must pay attention in scapes characters...
1 [adding mapped folders]
	...
	mapped_folders = [
		{ relative_server = "mapinvs" }
		, { relative_server = "anotherMappedInvokersFolder" }
		, { relative_server = "elseOtherMappedInvokersFolder" }
	]
	...

2 [Also you can add mapped folders in canonical path way]
	...
	mapped_folders = [
		{ relative_server = "mapinvs" }
		{ canonical_path = "C:\\TEMP\\hpi\\mappedfolder" }
	]
	...