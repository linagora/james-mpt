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

package org.apache.james.experimental.imapserver.processor.imap4rev1;

import org.apache.avalon.framework.logger.Logger;
import org.apache.james.experimental.imapserver.AuthorizationException;
import org.apache.james.experimental.imapserver.ImapSession;
import org.apache.james.experimental.imapserver.ProtocolException;
import org.apache.james.experimental.imapserver.commands.ImapCommand;
import org.apache.james.experimental.imapserver.message.IdRange;
import org.apache.james.experimental.imapserver.message.ImapResponseMessage;
import org.apache.james.experimental.imapserver.message.request.AbstractImapRequest;
import org.apache.james.experimental.imapserver.message.request.imap4rev1.CopyRequest;
import org.apache.james.experimental.imapserver.message.response.imap4rev1.BadResponse;
import org.apache.james.experimental.imapserver.message.response.imap4rev1.CommandCompleteResponse;
import org.apache.james.experimental.imapserver.processor.AbstractImapRequestProcessor;
import org.apache.james.experimental.imapserver.store.MailboxException;
import org.apache.james.mailboxmanager.GeneralMessageSet;
import org.apache.james.mailboxmanager.MailboxManagerException;
import org.apache.james.mailboxmanager.impl.GeneralMessageSetImpl;
import org.apache.james.mailboxmanager.mailbox.ImapMailboxSession;


public class CopyProcessor extends AbstractImapRequestProcessor {
	
	protected ImapResponseMessage doProcess(AbstractImapRequest message, ImapSession session, String tag, ImapCommand command) throws MailboxException, AuthorizationException, ProtocolException {
		final ImapResponseMessage result;
		if (message instanceof CopyRequest) {
			final CopyRequest request = (CopyRequest) message;
			result = doProcess(request, session, tag, command);
		} else {
			final Logger logger = getLogger();
			if (logger != null)
			{
				logger.debug("Expected CopyRequest, was " + message);
			}
			result = new BadResponse("Command unknown by Copy processor.");
		}
		
		return result;
	}

	private ImapResponseMessage doProcess(CopyRequest request, ImapSession session, String tag, ImapCommand command) throws MailboxException, AuthorizationException, ProtocolException {
		final String mailboxName = request.getMailboxName();
		final IdRange[] idSet = request.getIdSet();
		final boolean useUids = request.isUseUids();
		final ImapResponseMessage result = doProcess(mailboxName, idSet, useUids, session, tag, command);
		return result;
	}
	
	private ImapResponseMessage doProcess(String mailboxName, IdRange[] idSet, boolean useUids,
			ImapSession session, String tag, ImapCommand command) throws MailboxException, AuthorizationException, ProtocolException {
        ImapMailboxSession currentMailbox = session.getSelected().getMailbox();
        try {
            String fullMailboxName = session.buildFullName(mailboxName);
            if (!session.getMailboxManager().existsMailbox(fullMailboxName)) {
                MailboxException e=new MailboxException("Mailbox does not exists");
                e.setResponseCode( "TRYCREATE" );
                throw e;
            }
            for (int i = 0; i < idSet.length; i++) {
                GeneralMessageSet messageSet=GeneralMessageSetImpl.range(idSet[i].getLowVal(),idSet[i].getHighVal(),useUids);
                session.getMailboxManager().copyMessages(currentMailbox,messageSet,fullMailboxName);
            }
        } catch (MailboxManagerException e) {
            throw new MailboxException(e);
        } 
        final CommandCompleteResponse result = 
            new CommandCompleteResponse(useUids, command, tag);
        return result;
	}
}