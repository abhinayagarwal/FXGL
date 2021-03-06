/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.almasb.fxgl.ai.msg;

/**
 * The {@code MessageManager} is a singleton {@link MessageDispatcher} in charge of the creation, dispatch, and management of
 * telegrams.
 *
 * @author davebaol
 */
public final class MessageManager extends MessageDispatcher {

    private static final MessageManager INSTANCE = new MessageManager();

    /**
     * Don't let anyone else instantiate this class
     */
    private MessageManager() {
    }

    /**
     * Returns the singleton instance of the message dispatcher.
     */
    public static MessageManager getInstance() {
        return INSTANCE;
    }
}
