
# File:        ontocatdb/R/generated/java/ontocatdb/R/Sample_annotations.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity Sample_annotations
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for Sample_annotations
.create.sample_annotations <- function(data_frame, value_list, .usesession=T, .verbose=T)
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

#freely find Sample_annotations 
find.sample_annotations <- function( annotations_id=NULL, annotations_name=NULL , sample_id=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "ontocatdb.Sample_annotations", mget(ls(),environment()), .verbose=.verbose)
	return(result)
}

#add data.frame of Sample_annotations or each column individually
#note: each column must have the same length
add.sample_annotations <- function(.data_frame=NULL, annotations_id=NULL, annotations_name=NULL, sample_id=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.sample_annotations(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("ontocatdb.Sample_annotations", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of Sample_annotations or just one row using named arguments.
remove.sample_annotations <- function( .data_frame=NULL, annotations=NULL, sample=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.sample_annotations(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("ontocatdb.Sample_annotations", .data_frame, "REMOVE") )
}

use.sample_annotations<-function(annotations=NULL)
{
	#add session parameters
    
    #retrieve the sample_annotations by pkey or skey
    row<-F
    if(!is.null(annotations))
    {
    	row<-find.sample_annotations(annotations=annotations) 
    }  
    else
    {
    	stop('you need to provide {annotations}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using sample_annotations with:\n")
    	cat("\tannotations=",row$annotations,"\n")
		.MOLGENIS$session.sample_annotations.annotations<<-row$annotations
    }
    else
    {
       cat("Did not find sample_annotations using ","annotations=",annotations,"\n")
    }
}