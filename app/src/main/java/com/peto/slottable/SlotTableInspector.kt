package com.peto.slottable

import android.util.Log
import androidx.compose.runtime.Composition

/**
 * SlotTable Inspector
 *
 * ⚠️ WARNING: DEBUG ONLY - NEVER USE IN PRODUCTION ⚠️
 *
 * This utility uses reflection to access internal Compose APIs.
 * These APIs are not part of the public API contract and may change
 * without notice in future Compose versions.
 */
object SlotTableInspector {

    private const val TAG = "SlotTableInspector"

    /**
     * Logs the internal structure of SlotTable's groups IntArray
     *
     * SlotTable structure:
     * - Each group occupies 5 slots in the groups IntArray
     * - The 5 fields per group are:
     *   [0] Key
     *   [1] Group info (flags, node count, etc.)
     *   [2] Parent anchor
     *   [3] Size
     *   [4] Data anchor
     */
    fun logSlotTable(composition: Composition?) {
        if (composition == null) {
            Log.e(TAG, "Composition is null")
            return
        }

        try {
            // Access the slotTable field via reflection
            val compositionClass = composition.javaClass
            val slotTableField = compositionClass.getDeclaredField("slotTable")
            slotTableField.isAccessible = true
            val slotTable = slotTableField.get(composition)

            if (slotTable == null) {
                Log.e(TAG, "SlotTable is null")
                return
            }

            // Access the groups IntArray via reflection
            val slotTableClass = slotTable.javaClass
            val groupsField = slotTableClass.getDeclaredField("groups")
            groupsField.isAccessible = true
            val groups = groupsField.get(slotTable) as? IntArray

            if (groups == null) {
                Log.e(TAG, "Groups array is null")
                return
            }

            // Log the SlotTable structure
            val groupCount = groups.size / 5
            Log.d(TAG, "========================================")
            Log.d(TAG, "SlotTable Structure")
            Log.d(TAG, "========================================")
            Log.d(TAG, "Total groups: $groupCount")
            Log.d(TAG, "Groups array size: ${groups.size}")
            Log.d(TAG, "========================================")

            // Log each group's 5 fields
            for (i in 0 until groupCount) {
                val baseIndex = i * 5

                Log.d(TAG, "")
                Log.d(TAG, "--- 그룹 $i ---")
                Log.d(TAG, "Key          : ${groups[baseIndex + 0]}")
                Log.d(TAG, "Group info   : ${groups[baseIndex + 1]}")
                Log.d(TAG, "Parent anchor: ${groups[baseIndex + 2]}")
                Log.d(TAG, "Size         : ${groups[baseIndex + 3]}")
                Log.d(TAG, "Data anchor  : ${groups[baseIndex + 4]}")
            }

            Log.d(TAG, "")
            Log.d(TAG, "========================================")
            Log.d(TAG, "SlotTable 로깅 완료")
            Log.d(TAG, "========================================")

        } catch (e: NoSuchFieldException) {
            Log.e(TAG, "Failed to access field via reflection: ${e.message}", e)
            Log.e(TAG, "This may happen if Compose internal APIs have changed")
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "Failed to access field due to access restrictions: ${e.message}", e)
        } catch (e: ClassCastException) {
            Log.e(TAG, "Failed to cast field to expected type: ${e.message}", e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while inspecting SlotTable: ${e.message}", e)
        }
    }
}
