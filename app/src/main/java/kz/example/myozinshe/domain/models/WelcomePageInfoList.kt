package kz.example.myozinshe.domain.models

import kz.example.myozinshe.R

enum class WelcomePageInfoList(val imageRestId: Int, val title: String, val description: String) {
    FIRST(R.drawable.welcome_back_1, "ÖZINŞE-ге қош келдің!","Фильмдер, телехикаялар, ситкомдар, анимациялық жобалар, телебағдарламалар мен реалити-шоулар, аниме және тағы басқалары"),
    SECOND(R.drawable.welcome_back_2, "ÖZINŞE-ге қош келдің!","Кез келген құрылғыдан қара Сүйікті фильміңді қосымша төлемсіз телефоннан, планшеттен, ноутбуктан қара"),
    THIRD(R.drawable.welcome_back_3, "ÖZINŞE-ге қош келдің!","Тіркелу оңай. Қазір тіркел де қалаған фильміңе қол жеткіз")
}