#Please include your name and time the file was modified here, and write your alter staetments
#Also Inform Anoop when you create a new table or alter a column, so that the dump can be taken accordingly


#Added By akash on 07 April
Alter TABLE newfi_schema.uploadedfileslist
ADD COLUMN is_miscellaneous TINYINT(1) DEFAULT 1;