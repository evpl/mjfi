/*
 * Copyright (c) 2021 Evgenii Plugatar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <strong>MJFI - The Missing Java Functional Interfaces</strong>
 * <br><br>
 * <table>
 * <caption>List of interfaces (include {@link java.util.function} package)</caption>
 * <tr><th> Input            </th><th> Output  </th><th> Functional interface                                </th></tr>
 * <tr><td> void             </td><td> T       </td><td> {@link java.util.function.Supplier}                 </td></tr>
 * <tr><td> T                </td><td> T       </td><td> {@link java.util.function.UnaryOperator}            </td></tr>
 * <tr><td> T                </td><td> R       </td><td> {@link java.util.function.Function}                 </td></tr>
 * <tr><td> T                </td><td> byte    </td><td> {@link com.plugatar.mjfi.ToByteFunction}            </td></tr>
 * <tr><td> T                </td><td> short   </td><td> {@link com.plugatar.mjfi.ToShortFunction}           </td></tr>
 * <tr><td> T                </td><td> int     </td><td> {@link java.util.function.ToIntFunction}            </td></tr>
 * <tr><td> T                </td><td> long    </td><td> {@link java.util.function.ToLongFunction}           </td></tr>
 * <tr><td> T                </td><td> float   </td><td> {@link com.plugatar.mjfi.ToFloatFunction}           </td></tr>
 * <tr><td> T                </td><td> double  </td><td> {@link java.util.function.ToDoubleFunction}         </td></tr>
 * <tr><td> T                </td><td> char    </td><td> {@link com.plugatar.mjfi.ToCharFunction}            </td></tr>
 * <tr><td> T                </td><td> boolean </td><td> {@link java.util.function.Predicate}                </td></tr>
 * <tr><td> T                </td><td> void    </td><td> {@link java.util.function.Consumer}                 </td></tr>
 * <tr><td> T, T             </td><td> T       </td><td> {@link java.util.function.BinaryOperator}           </td></tr>
 * <tr><td> T, U             </td><td> R       </td><td> {@link java.util.function.BiFunction}               </td></tr>
 * <tr><td> T, U             </td><td> byte    </td><td> {@link com.plugatar.mjfi.ToByteBiFunction}          </td></tr>
 * <tr><td> T, U             </td><td> short   </td><td> {@link com.plugatar.mjfi.ToShortBiFunction}         </td></tr>
 * <tr><td> T, U             </td><td> int     </td><td> {@link java.util.function.ToIntBiFunction}          </td></tr>
 * <tr><td> T, U             </td><td> long    </td><td> {@link java.util.function.ToLongBiFunction}         </td></tr>
 * <tr><td> T, U             </td><td> float   </td><td> {@link com.plugatar.mjfi.ToFloatBiFunction}         </td></tr>
 * <tr><td> T, U             </td><td> double  </td><td> {@link java.util.function.ToDoubleBiFunction}       </td></tr>
 * <tr><td> T, U             </td><td> char    </td><td> {@link com.plugatar.mjfi.ToCharBiFunction}          </td></tr>
 * <tr><td> T, U             </td><td> boolean </td><td> {@link java.util.function.BiPredicate}              </td></tr>
 * <tr><td> T, U             </td><td> void    </td><td> {@link java.util.function.BiConsumer}               </td></tr>
 * <tr><td> T, byte          </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjByteFunction}           </td></tr>
 * <tr><td> T, byte          </td><td> byte    </td><td> {@link com.plugatar.mjfi.ObjByteToByteFunction}     </td></tr>
 * <tr><td> T, byte          </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjBytePredicate}          </td></tr>
 * <tr><td> T, byte          </td><td> void    </td><td> {@link com.plugatar.mjfi.ObjByteConsumer}           </td></tr>
 * <tr><td> T, short         </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjShortFunction}          </td></tr>
 * <tr><td> T, short         </td><td> short   </td><td> {@link com.plugatar.mjfi.ObjShortToShortFunction}   </td></tr>
 * <tr><td> T, short         </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjShortPredicate}         </td></tr>
 * <tr><td> T, short         </td><td> void    </td><td> {@link com.plugatar.mjfi.ObjShortConsumer}          </td></tr>
 * <tr><td> T, int           </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjIntFunction}            </td></tr>
 * <tr><td> T, int           </td><td> int     </td><td> {@link com.plugatar.mjfi.ObjIntToIntFunction}       </td></tr>
 * <tr><td> T, int           </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjIntPredicate}           </td></tr>
 * <tr><td> T, int           </td><td> void    </td><td> {@link java.util.function.ObjIntConsumer}           </td></tr>
 * <tr><td> T, long          </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjLongFunction}           </td></tr>
 * <tr><td> T, long          </td><td> long    </td><td> {@link com.plugatar.mjfi.ObjLongToLongFunction}     </td></tr>
 * <tr><td> T, long          </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjLongPredicate}          </td></tr>
 * <tr><td> T, long          </td><td> void    </td><td> {@link java.util.function.ObjLongConsumer}          </td></tr>
 * <tr><td> T, float         </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjFloatFunction}          </td></tr>
 * <tr><td> T, float         </td><td> float   </td><td> {@link com.plugatar.mjfi.ObjFloatToFloatFunction}   </td></tr>
 * <tr><td> T, float         </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjFloatPredicate}         </td></tr>
 * <tr><td> T, float         </td><td> void    </td><td> {@link com.plugatar.mjfi.ObjFloatConsumer}          </td></tr>
 * <tr><td> T, double        </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjDoubleFunction}         </td></tr>
 * <tr><td> T, double        </td><td> double  </td><td> {@link com.plugatar.mjfi.ObjDoubleToDoubleFunction} </td></tr>
 * <tr><td> T, double        </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjDoublePredicate}        </td></tr>
 * <tr><td> T, double        </td><td> void    </td><td> {@link java.util.function.ObjDoubleConsumer}        </td></tr>
 * <tr><td> T, char          </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjCharFunction}           </td></tr>
 * <tr><td> T, char          </td><td> char    </td><td> {@link com.plugatar.mjfi.ObjCharToCharFunction}     </td></tr>
 * <tr><td> T, char          </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjCharPredicate}          </td></tr>
 * <tr><td> T, char          </td><td> void    </td><td> {@link com.plugatar.mjfi.ObjCharConsumer}           </td></tr>
 * <tr><td> T, boolean       </td><td> R       </td><td> {@link com.plugatar.mjfi.ObjBooleanFunction}        </td></tr>
 * <tr><td> T, boolean       </td><td> boolean </td><td> {@link com.plugatar.mjfi.ObjBooleanPredicate}       </td></tr>
 * <tr><td> T, boolean       </td><td> void    </td><td> {@link com.plugatar.mjfi.ObjBooleanConsumer}        </td></tr>
 * <tr><td> void             </td><td> byte    </td><td> {@link com.plugatar.mjfi.ByteSupplier}              </td></tr>
 * <tr><td> byte             </td><td> R       </td><td> {@link com.plugatar.mjfi.ByteFunction}              </td></tr>
 * <tr><td> byte             </td><td> byte    </td><td> {@link com.plugatar.mjfi.ByteUnaryOperator}         </td></tr>
 * <tr><td> byte             </td><td> short   </td><td> {@link com.plugatar.mjfi.ByteToShortFunction}       </td></tr>
 * <tr><td> byte             </td><td> int     </td><td> {@link com.plugatar.mjfi.ByteToIntFunction}         </td></tr>
 * <tr><td> byte             </td><td> long    </td><td> {@link com.plugatar.mjfi.ByteToLongFunction}        </td></tr>
 * <tr><td> byte             </td><td> float   </td><td> {@link com.plugatar.mjfi.ByteToFloatFunction}       </td></tr>
 * <tr><td> byte             </td><td> double  </td><td> {@link com.plugatar.mjfi.ByteToDoubleFunction}      </td></tr>
 * <tr><td> byte             </td><td> char    </td><td> {@link com.plugatar.mjfi.ByteToCharFunction}        </td></tr>
 * <tr><td> byte             </td><td> boolean </td><td> {@link com.plugatar.mjfi.BytePredicate}             </td></tr>
 * <tr><td> byte             </td><td> void    </td><td> {@link com.plugatar.mjfi.ByteConsumer}              </td></tr>
 * <tr><td> byte, byte       </td><td> byte    </td><td> {@link com.plugatar.mjfi.ByteBinaryOperator}        </td></tr>
 * <tr><td> byte, byte       </td><td> boolean </td><td> {@link com.plugatar.mjfi.ByteBiPredicate}           </td></tr>
 * <tr><td> byte, byte       </td><td> R       </td><td> {@link com.plugatar.mjfi.ByteBiFunction}            </td></tr>
 * <tr><td> byte, byte       </td><td> void    </td><td> {@link com.plugatar.mjfi.ByteBiConsumer}            </td></tr>
 * <tr><td> void             </td><td> short   </td><td> {@link com.plugatar.mjfi.ShortSupplier}             </td></tr>
 * <tr><td> short            </td><td> R       </td><td> {@link com.plugatar.mjfi.ShortFunction}             </td></tr>
 * <tr><td> short            </td><td> byte    </td><td> {@link com.plugatar.mjfi.ShortToByteFunction}       </td></tr>
 * <tr><td> short            </td><td> short   </td><td> {@link com.plugatar.mjfi.ShortUnaryOperator}        </td></tr>
 * <tr><td> short            </td><td> int     </td><td> {@link com.plugatar.mjfi.ShortToIntFunction}        </td></tr>
 * <tr><td> short            </td><td> long    </td><td> {@link com.plugatar.mjfi.ShortToLongFunction}       </td></tr>
 * <tr><td> short            </td><td> float   </td><td> {@link com.plugatar.mjfi.ShortToFloatFunction}      </td></tr>
 * <tr><td> short            </td><td> double  </td><td> {@link com.plugatar.mjfi.ShortToDoubleFunction}     </td></tr>
 * <tr><td> short            </td><td> char    </td><td> {@link com.plugatar.mjfi.ShortToCharFunction}       </td></tr>
 * <tr><td> short            </td><td> boolean </td><td> {@link com.plugatar.mjfi.ShortPredicate}            </td></tr>
 * <tr><td> short            </td><td> void    </td><td> {@link com.plugatar.mjfi.ShortConsumer}             </td></tr>
 * <tr><td> short, short     </td><td> short   </td><td> {@link com.plugatar.mjfi.ShortBinaryOperator}       </td></tr>
 * <tr><td> short, short     </td><td> boolean </td><td> {@link com.plugatar.mjfi.ShortBiPredicate}          </td></tr>
 * <tr><td> short, short     </td><td> R       </td><td> {@link com.plugatar.mjfi.ShortBiFunction}           </td></tr>
 * <tr><td> short, short     </td><td> void    </td><td> {@link com.plugatar.mjfi.ShortBiConsumer}           </td></tr>
 * <tr><td> void             </td><td> int     </td><td> {@link java.util.function.IntSupplier}              </td></tr>
 * <tr><td> int              </td><td> R       </td><td> {@link java.util.function.IntFunction}              </td></tr>
 * <tr><td> int              </td><td> byte    </td><td> {@link com.plugatar.mjfi.IntToByteFunction}         </td></tr>
 * <tr><td> int              </td><td> short   </td><td> {@link com.plugatar.mjfi.IntToShortFunction}        </td></tr>
 * <tr><td> int              </td><td> int     </td><td> {@link java.util.function.IntUnaryOperator}         </td></tr>
 * <tr><td> int              </td><td> long    </td><td> {@link java.util.function.IntToLongFunction}        </td></tr>
 * <tr><td> int              </td><td> float   </td><td> {@link com.plugatar.mjfi.IntToFloatFunction}        </td></tr>
 * <tr><td> int              </td><td> double  </td><td> {@link java.util.function.IntToDoubleFunction}      </td></tr>
 * <tr><td> int              </td><td> char    </td><td> {@link com.plugatar.mjfi.IntToCharFunction}         </td></tr>
 * <tr><td> int              </td><td> boolean </td><td> {@link java.util.function.IntPredicate}             </td></tr>
 * <tr><td> int              </td><td> void    </td><td> {@link java.util.function.IntConsumer}              </td></tr>
 * <tr><td> int, int         </td><td> int     </td><td> {@link java.util.function.IntBinaryOperator}        </td></tr>
 * <tr><td> int, int         </td><td> boolean </td><td> {@link com.plugatar.mjfi.IntBiPredicate}            </td></tr>
 * <tr><td> int, int         </td><td> R       </td><td> {@link com.plugatar.mjfi.IntBiFunction}             </td></tr>
 * <tr><td> int, int         </td><td> void    </td><td> {@link com.plugatar.mjfi.IntBiConsumer}             </td></tr>
 * <tr><td> void             </td><td> long    </td><td> {@link java.util.function.LongSupplier}             </td></tr>
 * <tr><td> long             </td><td> R       </td><td> {@link java.util.function.LongFunction}             </td></tr>
 * <tr><td> long             </td><td> byte    </td><td> {@link com.plugatar.mjfi.LongToByteFunction}        </td></tr>
 * <tr><td> long             </td><td> short   </td><td> {@link com.plugatar.mjfi.LongToShortFunction}       </td></tr>
 * <tr><td> long             </td><td> int     </td><td> {@link java.util.function.LongToIntFunction}        </td></tr>
 * <tr><td> long             </td><td> long    </td><td> {@link java.util.function.LongUnaryOperator}        </td></tr>
 * <tr><td> long             </td><td> float   </td><td> {@link com.plugatar.mjfi.LongToFloatFunction}       </td></tr>
 * <tr><td> long             </td><td> double  </td><td> {@link java.util.function.LongToDoubleFunction}     </td></tr>
 * <tr><td> long             </td><td> char    </td><td> {@link com.plugatar.mjfi.LongToCharFunction}        </td></tr>
 * <tr><td> long             </td><td> boolean </td><td> {@link java.util.function.LongPredicate}            </td></tr>
 * <tr><td> long             </td><td> void    </td><td> {@link java.util.function.LongConsumer}             </td></tr>
 * <tr><td> long, long       </td><td> long    </td><td> {@link java.util.function.LongBinaryOperator}        </td></tr>
 * <tr><td> long, long       </td><td> boolean </td><td> {@link com.plugatar.mjfi.LongBiPredicate}           </td></tr>
 * <tr><td> long, long       </td><td> R       </td><td> {@link com.plugatar.mjfi.LongBiFunction}            </td></tr>
 * <tr><td> long, long       </td><td> void    </td><td> {@link com.plugatar.mjfi.LongBiConsumer}            </td></tr>
 * <tr><td> void             </td><td> float   </td><td> {@link com.plugatar.mjfi.FloatSupplier}             </td></tr>
 * <tr><td> float            </td><td> R       </td><td> {@link com.plugatar.mjfi.FloatFunction}             </td></tr>
 * <tr><td> float            </td><td> byte    </td><td> {@link com.plugatar.mjfi.FloatToByteFunction}       </td></tr>
 * <tr><td> float            </td><td> short   </td><td> {@link com.plugatar.mjfi.FloatToShortFunction}      </td></tr>
 * <tr><td> float            </td><td> int     </td><td> {@link com.plugatar.mjfi.FloatToIntFunction}        </td></tr>
 * <tr><td> float            </td><td> long    </td><td> {@link com.plugatar.mjfi.FloatToLongFunction}       </td></tr>
 * <tr><td> float            </td><td> float   </td><td> {@link com.plugatar.mjfi.FloatUnaryOperator}        </td></tr>
 * <tr><td> float            </td><td> double  </td><td> {@link com.plugatar.mjfi.FloatToDoubleFunction}     </td></tr>
 * <tr><td> float            </td><td> char    </td><td> {@link com.plugatar.mjfi.FloatToCharFunction}       </td></tr>
 * <tr><td> float            </td><td> boolean </td><td> {@link com.plugatar.mjfi.FloatPredicate}            </td></tr>
 * <tr><td> float            </td><td> void    </td><td> {@link com.plugatar.mjfi.FloatConsumer}             </td></tr>
 * <tr><td> float, float     </td><td> float   </td><td> {@link com.plugatar.mjfi.FloatBinaryOperator}       </td></tr>
 * <tr><td> float, float     </td><td> boolean </td><td> {@link com.plugatar.mjfi.FloatBiPredicate}          </td></tr>
 * <tr><td> float, float     </td><td> R       </td><td> {@link com.plugatar.mjfi.FloatBiFunction}           </td></tr>
 * <tr><td> float, float     </td><td> void    </td><td> {@link com.plugatar.mjfi.FloatBiConsumer}           </td></tr>
 * <tr><td> void             </td><td> double  </td><td> {@link java.util.function.DoubleSupplier}           </td></tr>
 * <tr><td> double           </td><td> R       </td><td> {@link java.util.function.DoubleFunction}           </td></tr>
 * <tr><td> double           </td><td> byte    </td><td> {@link com.plugatar.mjfi.DoubleToByteFunction}      </td></tr>
 * <tr><td> double           </td><td> short   </td><td> {@link com.plugatar.mjfi.DoubleToShortFunction}     </td></tr>
 * <tr><td> double           </td><td> int     </td><td> {@link java.util.function.DoubleToIntFunction}      </td></tr>
 * <tr><td> double           </td><td> long    </td><td> {@link java.util.function.DoubleToLongFunction}     </td></tr>
 * <tr><td> double           </td><td> float   </td><td> {@link com.plugatar.mjfi.DoubleToFloatFunction}     </td></tr>
 * <tr><td> double           </td><td> double  </td><td> {@link java.util.function.DoubleUnaryOperator}      </td></tr>
 * <tr><td> double           </td><td> char    </td><td> {@link com.plugatar.mjfi.DoubleToCharFunction}      </td></tr>
 * <tr><td> double           </td><td> boolean </td><td> {@link java.util.function.DoublePredicate}          </td></tr>
 * <tr><td> double           </td><td> void    </td><td> {@link java.util.function.DoubleConsumer}           </td></tr>
 * <tr><td> double, double   </td><td> double  </td><td> {@link java.util.function.DoubleBinaryOperator}     </td></tr>
 * <tr><td> double, double   </td><td> boolean </td><td> {@link com.plugatar.mjfi.DoubleBiPredicate}         </td></tr>
 * <tr><td> double, double   </td><td> R       </td><td> {@link com.plugatar.mjfi.DoubleBiFunction}          </td></tr>
 * <tr><td> double, double   </td><td> void    </td><td> {@link com.plugatar.mjfi.DoubleBiConsumer}          </td></tr>
 * <tr><td> void             </td><td> char    </td><td> {@link com.plugatar.mjfi.CharSupplier}              </td></tr>
 * <tr><td> char             </td><td> R       </td><td> {@link com.plugatar.mjfi.CharFunction}              </td></tr>
 * <tr><td> char             </td><td> byte    </td><td> {@link com.plugatar.mjfi.CharToByteFunction}        </td></tr>
 * <tr><td> char             </td><td> short   </td><td> {@link com.plugatar.mjfi.CharToShortFunction}       </td></tr>
 * <tr><td> char             </td><td> int     </td><td> {@link com.plugatar.mjfi.CharToIntFunction}         </td></tr>
 * <tr><td> char             </td><td> long    </td><td> {@link com.plugatar.mjfi.CharToLongFunction}        </td></tr>
 * <tr><td> char             </td><td> float   </td><td> {@link com.plugatar.mjfi.CharToFloatFunction}       </td></tr>
 * <tr><td> char             </td><td> double  </td><td> {@link com.plugatar.mjfi.CharToDoubleFunction}      </td></tr>
 * <tr><td> char             </td><td> char    </td><td> {@link com.plugatar.mjfi.CharUnaryOperator}         </td></tr>
 * <tr><td> char             </td><td> boolean </td><td> {@link com.plugatar.mjfi.CharPredicate}             </td></tr>
 * <tr><td> char             </td><td> void    </td><td> {@link com.plugatar.mjfi.CharConsumer}              </td></tr>
 * <tr><td> char, char       </td><td> char    </td><td> {@link com.plugatar.mjfi.CharBinaryOperator}        </td></tr>
 * <tr><td> char, char       </td><td> boolean </td><td> {@link com.plugatar.mjfi.CharBiPredicate}           </td></tr>
 * <tr><td> char, char       </td><td> R       </td><td> {@link com.plugatar.mjfi.CharBiFunction}            </td></tr>
 * <tr><td> char, char       </td><td> void    </td><td> {@link com.plugatar.mjfi.CharBiConsumer}            </td></tr>
 * <tr><td> void             </td><td> boolean </td><td> {@link java.util.function.BooleanSupplier}          </td></tr>
 * <tr><td> boolean          </td><td> R       </td><td> {@link com.plugatar.mjfi.BooleanFunction}           </td></tr>
 * <tr><td> boolean          </td><td> byte    </td><td> {@link com.plugatar.mjfi.BooleanToByteFunction}     </td></tr>
 * <tr><td> boolean          </td><td> short   </td><td> {@link com.plugatar.mjfi.BooleanToShortFunction}    </td></tr>
 * <tr><td> boolean          </td><td> int     </td><td> {@link com.plugatar.mjfi.BooleanToIntFunction}      </td></tr>
 * <tr><td> boolean          </td><td> long    </td><td> {@link com.plugatar.mjfi.BooleanToLongFunction}     </td></tr>
 * <tr><td> boolean          </td><td> float   </td><td> {@link com.plugatar.mjfi.BooleanToFloatFunction}    </td></tr>
 * <tr><td> boolean          </td><td> double  </td><td> {@link com.plugatar.mjfi.BooleanToDoubleFunction}   </td></tr>
 * <tr><td> boolean          </td><td> char    </td><td> {@link com.plugatar.mjfi.BooleanToCharFunction}     </td></tr>
 * <tr><td> boolean          </td><td> boolean </td><td> {@link com.plugatar.mjfi.BooleanUnaryOperator}      </td></tr>
 * <tr><td> boolean          </td><td> void    </td><td> {@link com.plugatar.mjfi.BooleanConsumer}           </td></tr>
 * <tr><td> boolean, boolean </td><td> boolean </td><td> {@link com.plugatar.mjfi.BooleanBinaryOperator}     </td></tr>
 * <tr><td> boolean, boolean </td><td> R       </td><td> {@link com.plugatar.mjfi.BooleanBiFunction}         </td></tr>
 * <tr><td> boolean, boolean </td><td> void    </td><td> {@link com.plugatar.mjfi.BooleanBiConsumer}         </td></tr>
 * </table>
 *
 * @see <a href="https://github.com/evpl/mjfi">GitHub repository</a>
 */
package com.plugatar.mjfi;
