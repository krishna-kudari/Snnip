package org.snnip.snnip

import com.intellij.codeInsight.completion.*

class CompletionContributor : com.intellij.codeInsight.completion.CompletionContributor() {
    init {
        extend(CompletionType.BASIC, CFCCompletionProvider.pattern(), CFCCompletionProvider())
        extend(CompletionType.BASIC, CFCPCompletionProvider.pattern(), CFCPCompletionProvider())
    }
}