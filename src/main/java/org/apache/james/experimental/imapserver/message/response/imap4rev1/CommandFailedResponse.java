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
import org.apache.james.experimental.imapserver.message.ImapResponseMessage;

public class CommandFailedResponse implements ImapResponseMessage {

    private final ImapCommand command;
    private final String responseCode;
    private final String reason;
    private final String tag;
    
    public CommandFailedResponse(final ImapCommand command, final String reason, String tag) {
        this(command, null, reason, tag);
    }
        
    public CommandFailedResponse(final ImapCommand command, final String responseCode, final String reason, String tag) {
        super();
        this.command = command;
        this.responseCode = responseCode;
        this.reason = reason;
        this.tag = tag;
    }

    public void encode(ImapResponse response, ImapSession session) {
        if (responseCode == null) {
            response.commandFailed(command, reason, tag);
        } else {
            response.commandFailed(command, responseCode, reason, tag);
        }
    }

}