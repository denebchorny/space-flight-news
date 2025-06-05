package com.denebchorny.buildlogic.convention.configs

data class SemanticVersioning(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val rc: Int,
    private val maxMajorDigits: Int = 2,
    private val maxMinorDigits: Int = 2,
    private val maxPatchDigits: Int = 2,
    private val maxRcDigits: Int = 2
) {
    val versionName: String
    val fullVersionName: String
    val versionCode: Int

    // Keep both versions of the parsed strings:
    // 1) Zero-padded for versionCode
    // 2) Trimmed for versionName and fullVersionName
    private val majorPadded = parseNumber("Major", major, maxMajorDigits)
    private val minorPadded = parseNumber("Minor", minor, maxMinorDigits)
    private val patchPadded = parseNumber("Patch", patch, maxPatchDigits)
    private val rcPadded    = parseNumber("Rc", rc, maxRcDigits)

    // Trim leading zeros for display
    private val majorDisplay = toDisplayString(majorPadded)
    private val minorDisplay = toDisplayString(minorPadded)
    private val patchDisplay = toDisplayString(patchPadded)
    private val rcDisplay    = toDisplayString(rcPadded)

    init {
        versionName = "$majorDisplay.$minorDisplay.$patchDisplay"
        fullVersionName = "$majorDisplay.$minorDisplay.$patchDisplay-rc$rcDisplay"

        // Combine the padded values for versionCode
        versionCode = "$majorPadded$minorPadded$patchPadded$rcPadded".toInt()
    }

    /**
     * Zero-pads the number up to [maxDigits].
     */
    private fun parseNumber(label: String, value: Int, maxDigits: Int): String {
        val maxValue = "9".repeat(maxDigits).toInt()
        val valueString = value.toString()
        val length = valueString.length

        return when {
            value < 0 -> error("$label cannot be negative")
            length > maxDigits -> error("$label cannot have more than $maxDigits digits")
            value > maxValue -> error("$label value cannot be greater than $maxValue")
            length < maxDigits -> valueString.padStart(maxDigits, '0')
            else -> valueString
        }
    }

    /**
     * Trims leading zeros but ensures at least "0" if the string is all zeros.
     */
    private fun toDisplayString(padded: String) =
        padded.trimStart('0').ifEmpty { "0" }
}