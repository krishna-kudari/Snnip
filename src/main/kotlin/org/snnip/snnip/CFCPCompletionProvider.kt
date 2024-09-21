package org.snnip.snnip

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.editor.Editor
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class CFCPCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        resultSet: CompletionResultSet
    ) {
        // Create the autocomplete suggestion for "CFCP"
        if (parameters.position.text.contains("CFCP")) {
            val fileName = getFileName(parameters.originalFile)

            // Create the completion snippet with @Preview
            val completion = """
                @Composable
                fun $fileName() {
                    // TODO: Implement $fileName
                }
                
                @Preview(showBackground = true)
                @Composable
                fun Preview_$fileName() {
                    MaterialTheme {
                        $fileName()
                    }
                }
            """.trimIndent()

            resultSet.addElement(
                LookupElementBuilder.create("CFCP").withInsertHandler { context, _ ->
                    insertSnippetAndRemoveText(context.editor, context.file, completion, "CFCP")
                }
            )
        }
    }

    // Extract the file name from the PsiFile object
    private fun getFileName(psiFile: PsiFile): String {
        return psiFile.name.substringBeforeLast('.')
    }

    // Inserts the snippet and removes "CFCP" from the editor
    private fun insertSnippetAndRemoveText(editor: Editor, file: PsiFile, snippet: String, triggerText: String) {
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