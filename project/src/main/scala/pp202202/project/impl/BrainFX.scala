package pp202202.project.impl

object BrainFX {
  /**
   * `interpreter` function is the interpreter of the BrainFxxk language.
   *
   * `iter` function is the handler for the character of position `pos`
   *   with the 32-slots memory `slots` and the input counter `inCnt`.
   *
   * Currently we have given the handlers for the character '>' and ','.
   * You should implement handlers for the other characters.
   */
  val brainFXInterpreter: String = """
     |(let
     |  (def genSlots (len ls)
     |     (if (= len 0) ls
     |         (app genSlots (- len 1) (cons 0 ls))))
     |  (val initSlots (app genSlots 32 nil))
     |
     |  (def reverse (lst result)
     |    (if (nil? lst) result (app reverse (snd lst) (cons (fst lst) result))))
     |  (def getInput (nth) (effect "getInput" nth))
     |  (def getChar (str n) (substr str n (+ n 1)))
     |
     |  (def handlers
     |    (char
     |      (by-name hNext)
     |      (by-name hPrev)
     |      (by-name hPlus)
     |      (by-name hMinus)
     |      (by-name hDot)
     |      (by-name hComma)
     |      (by-name hLPar)
     |      (by-name hRPar)
     |      (by-name hDefault))
     |    (if (= char ">") hNext
     |    (if (= char "<") hPrev
     |    (if (= char "+") hPlus
     |    (if (= char "-") hMinus
     |    (if (= char ".") hDot
     |    (if (= char ",") hComma
     |    (if (= char "[") hLPar
     |    (if (= char "]") hRPar
     |        hDefault)))))))))
     |
     |  (def moveRight (slot)
     |    (let
     |      (val rev (app reverse (snd slot) nil))
     |      (app reverse (cons (fst slot) rev) nil)))
     |  (def write (slots c) (cons c (snd slots)))
     |
     |  (def setNext (pos slots inCnt output)
     |       (cons pos (cons slots (cons inCnt (cons output nil)))))
     |
     |  (def iter (code pos slots inCnt output)
     |    (if
     |      (= (len code) pos)
     |      output
     |      (let
     |        (val char (app getChar code pos))
     |        (val next
     |          (app handlers char
     |            (app setNext (+ pos 1) (app moveRight slots) inCnt output)
     |            # TODO: '<' handler
     |            # TODO: '+' handler
     |            # TODO: '-' handler
     |            # TODO: '.' handler
     |            (app setNext (+ pos 1) (app write slots (app getInput inCnt)) (+ inCnt 1) output)
     |            # TODO: "[" handler
     |            # TODO: "]" handler
     |            (app setNext (+ pos 1) slots inCnt output)))
     |        (val nextPos (fst next))
     |        (val nextSlots (fst (snd next)))
     |        (val nextInCnt (fst (snd (snd next))))
     |        (val nextOutput (fst (snd (snd (snd next)))))
     |        (app iter code nextPos nextSlots nextInCnt nextOutput)
     |      )
     |    )
     |  )
     |
     |  (def interpreter (str) (app iter str 0 initSlots 0 nil))
     |
     |  interpreter
     |)
      """.stripMargin
}
