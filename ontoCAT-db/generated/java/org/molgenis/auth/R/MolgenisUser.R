
# File:        org.molgenis.auth/R/generated/java/org/molgenis/auth/R/MolgenisUser.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity MolgenisUser
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for MolgenisUser
.create.molgenisuser <- function(data_frame, value_list, .usesession=T, .verbose=T)
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

#freely find MolgenisUser 
find.molgenisuser <- function( id=NULL , name=NULL , password=NULL , emailaddress=NULL , activationcode=NULL , active=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "org.molgenis.auth.MolgenisUser", mget(ls(),environment()), .verbose=.verbose)
	#use secondary key as rownames
	#rownames(result)<-result$name
	return(result)
}

#add data.frame of MolgenisUser or each column individually
#note: each column must have the same length
add.molgenisuser <- function(.data_frame=NULL, name=NULL, password=NULL, emailaddress=NULL, activationcode=NULL, active=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.molgenisuser(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUser", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of MolgenisUser or just one row using named arguments.
remove.molgenisuser <- function( .data_frame=NULL, id=NULL, name=NULL, emailaddress=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.molgenisuser(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUser", .data_frame, "REMOVE") )
}

use.molgenisuser<-function(id=NULL, name=NULL, emailaddress=NULL)
{
	#add session parameters
    
    #retrieve the molgenisuser by pkey or skey
    row<-F
    if(!is.null(id))
    {
    	row<-find.molgenisuser(id=id) 
    }  
	else if( !(is.null(name)) )
	{
		row<-find.molgenisuser(name=name)
	} 
	else if( !(is.null(emailaddress)) )
	{
		row<-find.molgenisuser(emailaddress=emailaddress)
	} 
    else
    {
    	stop('you need to provide {id} or {name} or {emailaddress}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using molgenisuser with:\n")
    	cat("\tid=",row$id,"\n")
		.MOLGENIS$session.molgenisuser.id<<-row$id
		cat("\tname=",row$name,"\n")
		.MOLGENIS$session.molgenisuser.name<<-row$name
		cat("\temailaddress=",row$emailaddress,"\n")
		.MOLGENIS$session.molgenisuser.emailaddress<<-row$emailaddress
    }
    else
    {
       cat("Did not find molgenisuser using ","id=",id,"name=",name,"emailaddress=",emailaddress,"\n")
    }
}