/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.lsp.internal.completion;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.junit.jupiter.api.Test;

import com.github.cameltooling.lsp.internal.AbstractCamelLanguageServerTest;
import com.github.cameltooling.lsp.internal.CamelLanguageServer;

class KameletCompletionTest extends AbstractCamelLanguageServerTest {
	
	@Test
	void testKameletTemplateIdCompletion() throws Exception {
		CamelLanguageServer languageServer = initLanguageServer();
		
		List<CompletionItem> completions = getCompletionFor(languageServer, new Position(0, 19)).get().getLeft();
		
		CompletionItem completionItem = new CompletionItem("aws-ddb-streams-source");
		completionItem.setTextEdit(
				Either.forLeft(
						new TextEdit(
								new Range(new Position(0, 19), new Position(0, 19)),
								"aws-ddb-streams-source")));
		completionItem.setDocumentation("Receive events from AWS DynamoDB Streams.");
		assertThat(completions)
			.hasSizeGreaterThan(10)
			.contains(completionItem);
	}
		
	private CamelLanguageServer initLanguageServer() throws URISyntaxException, InterruptedException, ExecutionException {
		String text = "<from uri=\"kamelet:\" xmlns=\"http://camel.apache.org/schema/blueprint\"></from>\n";
		return initializeLanguageServer(text, ".xml");
	}
	
}
