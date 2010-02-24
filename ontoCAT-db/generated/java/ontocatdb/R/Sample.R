
# File:        ontocatdb/R/generated/java/ontocatdb/R/Sample.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity Sample
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for Sample
.create.sample <- function(data_frame, value_list, .usesession=T, .verbose=T)
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

#freely find Sample 
find.sample <- function( id=NULL , name=NULL , annotations=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "ontocatdb.Sample", mget(ls(),environment()), .verbose=.verbose)
	return(result)
}

#add data.frame of Sample or each column individually
#note: each column must have the same length
add.sample <- function(.data_frame=NULL, name=NULL, annotations=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.sample(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("ontocatdb.Sample", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of Sample or just one row using named arguments.
remove.sample <- function( .data_frame=NULL, id=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.sample(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("ontocatdb.Sample", .data_frame, "REMOVE") )
}

use.sample<-function(id=NULL)
{
	#add session parameters
    
    #retrieve the sample by pkey or skey
    row<-F
    if(!is.null(id))
    {
    	row<-find.sample(id=id) 
    }  
    else
    {
    	stop('you need to provide {id}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using sample with:\n")
    	cat("\tid=",row$id,"\n")
		.MOLGENIS$session.sample.id<<-row$id
    }
    else
    {
       cat("Did not find sample using ","id=",id,"\n")
    }
}