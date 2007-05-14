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

package org.apache.james.experimental.imapserver.processor;

import org.apache.james.experimental.imapserver.ImapSession;
import org.apache.james.experimental.imapserver.message.ImapResponseMessage;
import org.apache.james.experimental.imapserver.message.request.AbstractImapRequest;

/**
 * <p>
 * Processable IMAP command.
 * </p>
 * <p>
 * <strong>Note:</strong> this is a transitional API
 * and is liable to change.
 * </p>
 */
public interface ImapRequestProcessor {
    
    /**
     * Performs processing of the command.
     * @param session <code>ImapSession</code> 
     * @return response, not  null
     */
    ImapResponseMessage process(AbstractImapRequest message, ImapSession session );
}