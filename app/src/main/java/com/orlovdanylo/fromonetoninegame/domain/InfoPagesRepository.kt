package com.orlovdanylo.fromonetoninegame.domain

import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPage

interface InfoPagesRepository {
    fun fetchPages(): HashMap<Int, InfoPage>
}