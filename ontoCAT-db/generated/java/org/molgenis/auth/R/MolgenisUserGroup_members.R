
# File:        org.molgenis.auth/R/generated/java/org/molgenis/auth/R/MolgenisUserGroup_members.R
# Copyright:   GBIC 2000-2,010, all rights reserved
# Date:        February 24, 2010
#
# generator:   org.molgenis.generators.R.REntityGen 3.3.2-testing
#
# This file provides action methods to MOLGENIS for entity MolgenisUserGroup_members
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

#create valid data_frame for MolgenisUserGroup_members
.create.molgenisusergroup_members <- function(data_frame, value_list, .usesession=T, .verbose=T)
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

#freely find MolgenisUserGroup_members 
find.molgenisusergroup_members <- function( members_id=NULL, members_name=NULL , molgenisusergroup_id=NULL , .usesession = T, .verbose=T )
{
	#add session parameters
    
	result <- MOLGENIS.find( "org.molgenis.auth.MolgenisUserGroup_members", mget(ls(),environment()), .verbose=.verbose)
	return(result)
}

#add data.frame of MolgenisUserGroup_members or each column individually
#note: each column must have the same length
add.molgenisusergroup_members <- function(.data_frame=NULL, members_id=NULL, members_name=NULL, molgenisusergroup_id=NULL, .usesession = T, .verbose=T )
{
	.data_frame = .create.molgenisusergroup_members(.data_frame, mget(ls(),environment()), .usesession = .usesession, .verbose = .verbose)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUserGroup_members", .data_frame, "ADD", .verbose=.verbose) )
}


#remove data.frame of MolgenisUserGroup_members or just one row using named arguments.
remove.molgenisusergroup_members <- function( .data_frame=NULL, members=NULL, molgenisusergroup=NULL, .usesession = T )
{	
	#todo: translate to skey to pkey
	.data_frame = .create.molgenisusergroup_members(.data_frame, mget(ls(),environment()), .usesession = .usesession)
   	return( MOLGENIS.update("org.molgenis.auth.MolgenisUserGroup_members", .data_frame, "REMOVE") )
}

use.molgenisusergroup_members<-function(members=NULL)
{
	#add session parameters
    
    #retrieve the molgenisusergroup_members by pkey or skey
    row<-F
    if(!is.null(members))
    {
    	row<-find.molgenisusergroup_members(members=members) 
    }  
    else
    {
    	stop('you need to provide {members}')
    }       
    
    #if exists, put in session
    if(!is.logical(row) && nrow(row) == 1)
    {
    	cat("Using molgenisusergroup_members with:\n")
    	cat("\tmembers=",row$members,"\n")
		.MOLGENIS$session.molgenisusergroup_members.members<<-row$members
    }
    else
    {
       cat("Did not find molgenisusergroup_members using ","members=",members,"\n")
    }
}