package org.snnip.snnip

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import icons.StudioIcons

class CFCCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        resultSet: CompletionResultSet
    ) {
        // Create the autocomplete suggestion for "CFC"
        if (parameters.position.text.contains("CFC")) {
            val fileName = getFileName(parameters.originalFile)

            // Create the completion suggestion
            val completion = "@Composable\nfun $fileName() {\n    // TODO: Implement $fileName\n}"
            resultSet.addElement(
                LookupElementBuilder.create("CFC").withInsertHandler { context, _ ->
                    insertSnippet(context.editor, context.file, completion, triggerText = "CFC")
                }
            )
        }
    }

    // Extract the file name from the PsiFile object
    private fun getFileName(psiFile: PsiFile): String {
        return psiFile.name.substringBeforeLast('.')
    }

    // Inserts the snippet at the caret position
    private fun insertSnippet(editor: Editor, file: PsiFile, snippet: String, triggerText: String) {
        val document = editor.document
        val caretOffset = editor.caretModel.offset

        // Find the range of the typed "CFCP" and delete it
        val startOffset = caretOffset - triggerText.length
        document.deleteString(startOffset, caretOffset)

        // Insert the snippet at the new caret position
        document.insertString(startOffset, snippet)
    }

    companion object {
        fun pattern() = PlatformPatterns.psiElement(PsiElement::class.java)
    }
}