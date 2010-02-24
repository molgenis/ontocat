
# File:        org.molgenis.auth/R/generated/java/org/molgenis/auth/R/MolgenisUserGroup.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity MolgenisUserGroup
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for MolgenisUserGroup
.create.molgenisusergroup <- function(data_frame, value_list, .usesession=T, .verbose=T)
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

#freely find MolgenisUserGroup 
find.molgenisusergroup <- function( id=NULL , name=NULL , superuser=NULL , members=NULL , canread=NULL , canwrite=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "org.molgenis.auth.MolgenisUserGroup", mget(ls(),environment()), .verbose=.verbose)
	#use secondary key as rownames
	#rownames(result)<-result$name
	return(result)
}

#add data.frame of MolgenisUserGroup or each column individually
#note: each column must have the same length
add.molgenisusergroup <- function(.data_frame=NULL, name=NULL, superuser=NULL, members=NULL, canread=NULL, canwrite=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.molgenisusergroup(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUserGroup", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of MolgenisUserGroup or just one row using named arguments.
remove.molgenisusergroup <- function( .data_frame=NULL, id=NULL, name=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.molgenisusergroup(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUserGroup", .data_frame, "REMOVE") )
}

use.molgenisusergroup<-function(id=NULL, name=NULL)
{
	#add session parameters
    
    #retrieve the molgenisusergroup by pkey or skey
    row<-F
    if(!is.null(id))
    {
    	row<-find.molgenisusergroup(id=id) 
    }  
	else if( !(is.null(name)) )
	{
		row<-find.molgenisusergroup(name=name)
	} 
    else
    {
    	stop('you need to provide {id} or {name}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using molgenisusergroup with:\n")
    	cat("\tid=",row$id,"\n")
		.MOLGENIS$session.molgenisusergroup.id<<-row$id
		cat("\tname=",row$name,"\n")
		.MOLGENIS$session.molgenisusergroup.name<<-row$name
    }
    else
    {
       cat("Did not find molgenisusergroup using ","id=",id,"name=",name,"\n")
    }
}