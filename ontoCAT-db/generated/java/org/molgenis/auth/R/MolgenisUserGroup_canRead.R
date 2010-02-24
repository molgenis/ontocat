
# File:        org.molgenis.auth/R/generated/java/org/molgenis/auth/R/MolgenisUserGroup_canRead.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity MolgenisUserGroup_canRead
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for MolgenisUserGroup_canRead
.create.molgenisusergroup_canread <- function(data_frame, value_list, .usesession=T, .verbose=T)
{
	#convert to data_frame, remove null columns
	if(!is.data.frame(data_frame))
	{
		if(is.matrix(data_frame))
		{
			data_frame <- as.data.frame(data_frame)
		}
		else if(is.list(data_frame))
		{
			data_frame <- as.data.frame(data_frame[!sapply(data_frame, is.null)])
		}
		#transform non-null values into data.frame
		else
		{
			data_frame <- as.data.frame(value_list[!sapply(value_list, is.null)])
		}
  	}	
  	  	  	
        
    return(data_frame)
}

#freely find MolgenisUserGroup_canRead 
find.molgenisusergroup_canread <- function( canread_id=NULL, canread_name=NULL , molgenisusergroup_id=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "org.molgenis.auth.MolgenisUserGroup_canRead", mget(ls(),environment()), .verbose=.verbose)
	return(result)
}

#add data.frame of MolgenisUserGroup_canRead or each column individually
#note: each column must have the same length
add.molgenisusergroup_canread <- function(.data_frame=NULL, canread_id=NULL, canread_name=NULL, molgenisusergroup_id=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.molgenisusergroup_canread(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUserGroup_canRead", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of MolgenisUserGroup_canRead or just one row using named arguments.
remove.molgenisusergroup_canread <- function( .data_frame=NULL, canread=NULL, molgenisusergroup=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.molgenisusergroup_canread(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUserGroup_canRead", .data_frame, "REMOVE") )
}

use.molgenisusergroup_canread<-function(canread=NULL)
{
	#add session parameters
    
    #retrieve the molgenisusergroup_canread by pkey or skey
    row<-F
    if(!is.null(canread))
    {
    	row<-find.molgenisusergroup_canread(canread=canread) 
    }  
    else
    {
    	stop('you need to provide {canread}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using molgenisusergroup_canread with:\n")
    	cat("\tcanread=",row$canread,"\n")
		.MOLGENIS$session.molgenisusergroup_canread.canread<<-row$canread
    }
    else
    {
       cat("Did not find molgenisusergroup_canread using ","canread=",canread,"\n")
    }
}