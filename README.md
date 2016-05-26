# PHorest Events

Defining the Phorest Cloud events model.

## Creating and SQS queue

## Grant PHorest Access to the Queue

{
    "Version": "2012-10-17",
    "Id": "PhorestEvents_Policy_UUID",
    "Statement": [
	{
	    "Sid":"Phorest_Events_SendMessage",
	    "Effect": "Allow",
	    "Principal": {
		"AWS": "012345678901"
	    },
	    "Action": "sqs:SendMessage",
	    "Resource": "arn:aws:sqs:eu-west-1::phorest_events"
	}
    ]  
}
