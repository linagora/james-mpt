/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.experimental.imapserver.message.response.imap4rev1;

import org.apache.james.experimental.imapserver.ImapResponse;
import org.apache.james.experimental.imapserver.ImapSession;
import org.apache.james.experimental.imapserver.commands.ImapCommand;
import org.apache.james.experimental.imapserver.message.response.AbstractImapResponse;
import org.apache.james.experimental.imapserver.store.MailboxException;

public class SearchResponse extends AbstractImapResponse {
    private final String idList;
    private final boolean useUids;
    
    public SearchResponse(final ImapCommand command, final String idList, 
            final boolean useUids, final String tag) {
        super(command, tag);
        this.idList = idList;
        this.useUids = useUids;
    }
    
    protected void doEncode(ImapResponse response, ImapSession session, ImapCommand command, String tag) throws MailboxException {
        
        response.commandResponse( command, idList );
        boolean omitExpunged = (!useUids);
        session.unsolicitedResponses( response, omitExpunged, useUids );
        response.commandComplete( command, tag );  
    }
    
}