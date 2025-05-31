package com.barissemerci.currencyexchanger.core.presentation.util

import com.barissemerci.currencyexchanger.R

fun getDrawableIdForCurrency(symbol: String): Int {
    return when (symbol.uppercase()) {
        "AOA" -> R.drawable.ic_aoa
        "ARS" -> R.drawable.ic_ars
        "AUD" -> R.drawable.ic_aud
        "AZN" -> R.drawable.ic_azn
        "BAM" -> R.drawable.ic_bam
        "BBD" -> R.drawable.ic_bbd
        "BDT" -> R.drawable.ic_bdt
        "BGN" -> R.drawable.ic_bgn
        "BHD" -> R.drawable.ic_bhd
        "BIF" -> R.drawable.ic_bif
        "BMD" -> R.drawable.ic_bmd
        "BND" -> R.drawable.ic_bnd
        "BOB" -> R.drawable.ic_bob
        "BRL" -> R.drawable.ic_brl
        "BTN" -> R.drawable.ic_btn
        "BYN" -> R.drawable.ic_byn
        "BZD" -> R.drawable.ic_bzd
        "DJF" -> R.drawable.ic_djf
        "DOP" -> R.drawable.ic_dop
        "DZD" -> R.drawable.ic_dzd
        "EGP" -> R.drawable.ic_egp
        "ETB" -> R.drawable.ic_etb
        "EUR" -> R.drawable.ic_eur
        "GBP" -> R.drawable.ic_gbp
        "GEL" -> R.drawable.ic_gel
        "GHS" -> R.drawable.ic_ghs
        "GIP" -> R.drawable.ic_gip
        "HKD" -> R.drawable.ic_hkd
        "HNL" -> R.drawable.ic_hnl
        "HUF" -> R.drawable.ic_huf
        "IDR" -> R.drawable.ic_idr
        "INR" -> R.drawable.ic_inr
        "ISK" -> R.drawable.ic_isk
        "JMD" -> R.drawable.ic_jmd
        "JOD" -> R.drawable.ic_jod
        "JPY" -> R.drawable.ic_jpy
        "KES" -> R.drawable.ic_kes
        "KGS" -> R.drawable.ic_kgs
        "KRW" -> R.drawable.ic_krw
        "KWD" -> R.drawable.ic_kwd
        "KZT" -> R.drawable.ic_kzt
        "LSL" -> R.drawable.ic_lsl
        "MAD" -> R.drawable.ic_mad
        "MDL" -> R.drawable.ic_mdl
        "MGA" -> R.drawable.ic_mga
        "MKD" -> R.drawable.ic_mkd
        "MVR" -> R.drawable.ic_mvr
        "MWK" -> R.drawable.ic_mwk
        "MXN" -> R.drawable.ic_mxn
        "MYR" -> R.drawable.ic_myr
        "MZN" -> R.drawable.ic_mzn
        "NAD" -> R.drawable.ic_nad
        "NGN" -> R.drawable.ic_ngn
        "NIO" -> R.drawable.ic_nio
        "NOK" -> R.drawable.ic_nok
        "NPR" -> R.drawable.ic_npr
        "PHP" -> R.drawable.ic_php
        "PLN" -> R.drawable.ic_pln
        "RON" -> R.drawable.ic_ron
        "RUB" -> R.drawable.ic_rub
        "RWF" -> R.drawable.ic_rwf
        "SAR" -> R.drawable.ic_sar
        "SEK" -> R.drawable.ic_sek
        "SGD" -> R.drawable.ic_sgd
        "SLL" -> R.drawable.ic_sll
        "SSP" -> R.drawable.ic_ssp
        "SZL" -> R.drawable.ic_szl
        "UGX" -> R.drawable.ic_ugx
        "USD" -> R.drawable.ic_usd
        "XAF" -> R.drawable.ic_xaf
        "XOF" -> R.drawable.ic_xof
        "ZAR" -> R.drawable.ic_zar
        "ZMW" -> R.drawable.ic_zmw
        "ZWL" -> R.drawable.ic_zwl
        else -> R.drawable.ic_unk
    }
}