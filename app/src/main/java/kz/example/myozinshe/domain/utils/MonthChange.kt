package kz.example.myozinshe.domain.utils

class MonthChange {
    private var monthName: String = "no"
    fun setMonthNames(month: Int): String {
        val monthNames = arrayOf(
            "Қаңтар",
            "Ақпан",
            "Наурыз",
            "Сәуір",
            "Мамыр",
            "Маусым",
            "Шілде",
            "Тамыз",
            "Қыркүйек",
            "Қазан",
            "Қараша",
            "Желтоқсан"
        )
        for (i in 1..12) {
            monthName = monthNames[i - 1]
        }

        return monthName
    }
}